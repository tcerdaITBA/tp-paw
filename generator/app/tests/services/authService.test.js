define(['services/authService', 'angular-mocks'], function() {

    describe('Auth Service', function() {
    	var authService, url, $httpBackend, $q;
		var DUMMY_USER = {id: '4', name: 'Pedro'};
		var USERNAME = 'pedro@gmail.com'; 
		var PASSWORD = 'passpass';
		var CREDENTIALS = {j_username: USERNAME, j_password: PASSWORD};
		var DUMMY_TOKEN = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789';
		var RESPONSE_ERROR = {detail: 'Not found.'};
		
		beforeEach(module('productSeek'));

    	beforeEach(inject(function(_authService_, _url_, _$httpBackend_, _$q_) {
    		authService = _authService_;
    		url = _url_;
    		$httpBackend = _$httpBackend_;
    		$q = _$q_;
    	}));
		
		it('should be defined', function() {
            expect(authService).toBeDefined();
        });
		
		describe('.logIn()', function() {
			it('should be defined', function() {
				expect(authService.logIn).toBeDefined();
			});
			
			it('should log in given a correct user and password', function() {
				$httpBackend.whenPOST(url + '/login', CREDENTIALS)
					.respond(function(method, url, data, headers){
						return [200, {}, {'X-AUTH-TOKEN': DUMMY_TOKEN}];
					});
				
				authService.logIn(USERNAME, PASSWORD);
				
				$httpBackend.flush();
				
				expect(authService.isLoggedIn()).toBe(true);
			});
			
			it('should NOT log in given an incorrect user or password', function() {
				$httpBackend.whenPOST(url + '/login', CREDENTIALS)
        			.respond(401, $q.reject({details: 'Authentication Failed'}));
				
				authService.logIn(USERNAME, PASSWORD);
				$httpBackend.flush();
				
				expect(authService.isLoggedIn()).toBe(false);
			});
		});
		
		describe('.logOut()', function() {
			it('should be defined', function() {
				expect(authService.logOut).toBeDefined();
			});
			
			it('should log out a logged session', function() {
				$httpBackend.whenPOST(url + '/login', CREDENTIALS)
					.respond(function(method, url, data, headers){
						return [200, {}, {'X-AUTH-TOKEN': DUMMY_TOKEN}];
					});

				authService.logIn(USERNAME, PASSWORD);
				$httpBackend.flush();
				var wasLoggedIn = authService.isLoggedIn();
				authService.logOut();
				expect(wasLoggedIn && !authService.isLoggedIn()).toBe(true);
			})
		});
		
		describe('.isLoggedIn()', function() {
			it('should be defined', function() {
				expect(authService.isLoggedIn).toBeDefined();
			});
			
			it('should not be true before logging in', function() {
				expect(authService.isLoggedIn()).toBe(false);
			});
		});
		
		// TODO: testear que se guarda bien el access token
	});
});