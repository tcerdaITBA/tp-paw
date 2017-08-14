define(['services/restService', 'productSeek', 'angular-mocks'], function() {

    describe('Rest Service', function() {
    	var restService, url, $httpBackend, $q;
    	var DUMMY_USER = {name: 'tomaco', id: '1'};
        var RESPONSE_ERROR = {detail: 'Not found.'};

    	beforeEach(module('productSeek'));

    	beforeEach(inject(function(_restService_, _url_, _$httpBackend_, _$q_) {
    		restService = _restService_;
    		url = _url_;
    		$httpBackend = _$httpBackend_;
    		$q = _$q_;
    	}));

        it('should be defined', function() {
            expect(restService).toBeDefined();
        });

        describe('.getUser()', function() {
            var response;

 			it('should be defined', function() {
				expect(restService.getUser).toBeDefined();
			})

        	it('should return a valid user given an id', function() {

        		$httpBackend.whenGET(url + '/users/' + DUMMY_USER.id).respond(200, $q.when(DUMMY_USER));

        		restService.getUser(DUMMY_USER.id)
        		.then(function(res) {
        			response = res;
        		});

        		$httpBackend.flush(); // Performs the http response

        		expect(response).toEqual(DUMMY_USER);
        	});

            it('should return with a 404 when called with an non-existent user id', function() {
                $httpBackend.whenGET(url + '/users/' + DUMMY_USER.id).respond(404, $q.reject(RESPONSE_ERROR));

                restService.getUser(DUMMY_USER.id)
                .catch(function(res) {
                    response = res;
                });

                $httpBackend.flush(); // Performs the http response

                expect(response).toEqual(RESPONSE_ERROR);

            });
        });

        describe('.')

    });
});
