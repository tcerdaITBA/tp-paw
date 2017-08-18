define(['services/restService', 'angular-mocks'], function() {

    describe('Rest Service', function() {
    	var restService, url, $httpBackend, $q;
    	var DUMMY_USER = {id: '1', name: 'tomaco'};
        var DUMMY_PRODUCTS = [{id: '1', name: 'slime rancher', category: 'game'}, 
                              {id: '2', name: 'apple watch', category: 'gadget'}];
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
			});

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
        
        describe('.getProducts()', function() {
            var response;

 			it('should be defined', function() {
				expect(restService.getProducts).toBeDefined();
			});
           
            it('should return a valid list of products given no parameters', function() {
        		$httpBackend.whenGET(url + '/products').respond(200, $q.when(DUMMY_PRODUCTS));

        		restService.getProducts()
        		.then(function(res) {
        			response = res;
        		});

        		$httpBackend.flush(); // Performs the http response

        		expect(response).toEqual(DUMMY_PRODUCTS);
            });
            
            it('should return a valid list of products given query parameters', function() {
        		$httpBackend.whenGET(url + '/products?per_page=1&page=1&sorted_by=votes&category=game&order=asc').respond(200, $q.when(DUMMY_PRODUCTS[0]));

        		restService.getProducts({pageSize: 1, page: 1, orderBy: 'votes', category: 'game', order: 'asc'})
        		.then(function(res) {
        			response = res;
        		});

        		$httpBackend.flush(); // Performs the http response

        		expect(response).toEqual(DUMMY_PRODUCTS[0]);
            });
        });
    });
});
