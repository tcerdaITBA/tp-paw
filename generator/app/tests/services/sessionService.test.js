define(['services/sessionService', 'angular-mocks'], function() {
	describe('Session Service', function() {
		var sessionService;
		var DUMMY_USER = {name: 'Pedro', id: 4};
		
		beforeEach(module('productSeek'));
		
		beforeEach(inject(function(_sessionService_) {
    		sessionService = _sessionService_;
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
	});
});
