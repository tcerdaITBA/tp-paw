define(['services/authService', 'angular-mocks'], function() {

    describe('Auth Service', function() {
    	var authService, url, $httpBackend, $q;
		var DUMMY_USER = {id: '4', name: 'Pedro'};
		var USERNAME = 'pedro@gmail.com'; 
		var PASSWORD = 'passpass';
		var CREDENTIALS = 'j_username='+encodeURIComponent(USERNAME)+'&j_password='+encodeURIComponent(PASSWORD);
		var DUMMY_TOKEN = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789';
		var RESPONSE_ERROR = {detail: 'Not found.'};
		
		beforeEach(module('productSeek'));

    	beforeEach(inject(function(_authService_, _url_, _$httpBackend_, _$q_) {
    		authService = _authService_;
    		url = _url_;
    		$httpBackend = _$httpBackend_;
    		$q = _$q_;
			
			$httpBackend.whenPOST(url + '/login', CREDENTIALS)
				.respond(200, $q.when(''), {headers: {'X-AUTH-TOKEN': DUMMY_TOKEN}});
			$httpBackend.whenGET(url + '/user', undefined, {'X-AUTH-TOKEN': DUMMY_TOKEN})
				.respond(200, $q.when(DUMMY_USER));
    	}));
		
		it('should be defined', function() {
            expect(authService).toBeDefined();
        });
		
		describe('.logIn()', function() {
			it('should be defined', function() {
				expect(authService.logIn).toBeDefined();
			});
			
			it('should log in given a correct user and password', function() {
				var logged = false;
				
				authService.logIn(USERNAME, PASSWORD)
				.then(function(response) {
					logged = authService.isLoggedIn();
				});
				
				$httpBackend.flush();

				expect(logged).toBe(true);
			});
			
			it('should NOT log in given an incorrect user or password', function() {
				var logged = true;
				var WRONG_CREDENTIALS = 'j_username='+encodeURIComponent(USERNAME)+'&j_password='+encodeURIComponent(PASSWORD + 'wrong');

				$httpBackend.expectPOST(url + '/login', WRONG_CREDENTIALS)
        			.respond(401, $q.reject({details: 'Authentication Failed'}));
				
				authService.logIn(USERNAME, PASSWORD + 'wrong')
				.catch(function(response) {
					logged = authService.isLoggedIn();
				});
				
				$httpBackend.flush();
				expect(logged).toBe(false);
			});
		});
		
		describe('.logOut()', function() {
			it('should be defined', function() {
				expect(authService.logOut).toBeDefined();
			});
			
			it('should log out a logged session', function() {
				var wasLoggedIn;

				authService.logIn(USERNAME, PASSWORD)
				.then(function(response) {
					wasLoggedIn = authService.isLoggedIn();
					authService.logOut();
				});
				
				$httpBackend.flush();
				expect(wasLoggedIn && !authService.isLoggedIn()).toBe(true);
			})
		});
		
		describe('.getLoggedUser()', function() {
			it('should be defined', function() {
				expect(authService.getLoggedUser).toBeDefined();
			});
			
			it('should return a valid user after logging in', function() {
				var user;

				authService.logIn(USERNAME, PASSWORD)
				.then(function(response) {
					user = authService.getLoggedUser();
				});
				
				$httpBackend.flush();
				expect(user).toEqual(DUMMY_USER);
			});
			
			it('should not return a user after logging out', function() {
				var user;

				authService.logIn(USERNAME, PASSWORD)
				.then(function(response) {
					authService.logOut();
					user = authService.getLoggedUser();
				});
				
				$httpBackend.flush();
				expect(user).not.toBeTruthy();
			});
		});
		
		// TODO: testear que se guarda bien el access token
	});
});