define(['services/sessionService', 'angular-mocks'], function() {
	describe('Session Service', function() {
		var sessionService;
		var DUMMY_USER = {name: 'Pedro', id: 4};
		var HISTORY_1 = 'first', HISTORY_2 = 'second', HISTORY_3 = 'third', HISTORY_4 = 'fourth', HISTORY_5 = 'fifth';
		
		beforeEach(module('productSeek'));
		
		beforeEach(inject(function(_sessionService_) {
    		sessionService = _sessionService_;
			sessionService.cleanSearchHistory();
    	}));
		
        it('should be defined', function() {
            expect(sessionService).toBeDefined();
        });
		
		describe('.getUser()', function() {
			it('should be defined', function() {
				expect(sessionService.getUser).toBeDefined();
			});
			
			it('should not have a user beforehand', function() {
				expect(sessionService.getUser()).toBeNull();
			});
			
			it('should get the same user that was saved', function() {
				sessionService.setUser(DUMMY_USER);
				expect(sessionService.getUser()).toEqual(DUMMY_USER);
			});
		});
		
		describe('.destroy()', function() {
			it('should be defined', function() {
				expect(sessionService.destroy).toBeDefined();
			});
			
			it('should erase a saved user', function() {
				sessionService.setUser(DUMMY_USER);
				sessionService.destroy();
				expect(sessionService.getUser()).toBeNull();
			});
		});
		
		describe('.saveToSearchHistory()', function() {
			it('should be defined', function() {
				expect(sessionService.saveToSearchHistory).toBeDefined();
			});
			
			it('should be empty beforehand', function() {
				expect(sessionService.getSearchHistory()).toEqual([]);
			});
			
			it('should save items in backwards chronological order', function() {
				var history = [HISTORY_1, HISTORY_2, HISTORY_3];
				var reversed = [HISTORY_1, HISTORY_2, HISTORY_3].reverse();
				
				history.forEach(function (elem) {
					sessionService.saveToSearchHistory(elem);
				});
				expect(sessionService.getSearchHistory()).toEqual(reversed);
			});
			
			it ('should not save repeated items', function() {
				var history = [HISTORY_1, HISTORY_1, HISTORY_1];
				
				history.forEach(function (elem) {
					sessionService.saveToSearchHistory(elem);
				});
				
				expect(sessionService.getSearchHistory()).toEqual([HISTORY_1]);
			})
			
			it('should not save more than 4 items', function() {
				var history = [HISTORY_1, HISTORY_2, HISTORY_3, HISTORY_4, HISTORY_5];
				var result = [HISTORY_2, HISTORY_3, HISTORY_4, HISTORY_5].reverse();
				
				history.forEach(function (elem) {
					sessionService.saveToSearchHistory(elem);
				});
				
				expect(sessionService.getSearchHistory()).toEqual(result);
			})
		});
		
		describe('.cleanSearchHistory()', function() {
			it('should be defined', function() {
				expect(sessionService.cleanSearchHistory).toBeDefined();
			});
			
			it('should empty the saved history', function() {
				var history = [HISTORY_1, HISTORY_2, HISTORY_3];
				
				history.forEach(function (elem) {
					sessionService.saveToSearchHistory(elem);
				});
				
				var wasNotEmpty = sessionService.getSearchHistory().length > 0;
				
				sessionService.cleanSearchHistory();
				
				var isEmpty = sessionService.getSearchHistory().length === 0;
				
				expect(wasNotEmpty && isEmpty).toBe(true);
			});
		});
	});
});
