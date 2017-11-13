define(['controllers/SearchCtrl', 'angular-mocks'], function() {
    describe('Search Controller', function() {
        var controller, scope, $controller;
        var DUMMY_PRODUCTS = {count: 2, 
                              products: [
                                  {id: '1', name: 'slime rancher', category: 'game'}, 
                                  {id: '2', name: 'slime watch', category: 'gadget'},
								  {id: '3', name: 'slime jingle', category: 'music'}
                              ]};
		var DUMMY_USERS = {count: 3, 
                              users: [
								  // TODO
                              ]};
		
        var QUERY = 'slime';
		
        beforeEach(module('productSeek'));
        
        beforeEach(inject(function($injector, $rootScope, _$controller_) {
			scope = $rootScope.$new();
			$controller = _$controller_;
			controller = $controller('SearchCtrl', { $scope: scope, productsData: DUMMY_PRODUCTS, query: QUERY, 
				usersData: DUMMY_USERS});
		}));
		
		describe('$scope.products', function() {
            it('should be defined with the resolved "productsData" products', function() {
                expect(scope.products).toEqual(DUMMY_PRODUCTS.products);
            });
        });
		
		describe('$scope.query', function() {
            it('should be defined with the resolved query', function() {
                expect(scope.query).toEqual(QUERY);
            });
        });
		
		describe('$scope.users', function() {
            it('should be defined with the "usersData" users', function() {
                expect(scope.users).toEqual(DUMMY_USERS.users);
            });
        });
		
		describe('$scope.tabs: Open tabs', function() {
			function productsTab(scope) {
				return scope.tabs[0];
			}

			function userTab(scope) {
				return scope.tabs[1];
			}
			
			it('should show products tab if there is any product', function() {
				expect(productsTab(scope)).toBe(true); 
				expect(userTab(scope)).toBe(false);
			});
			
			it('should show users tab if there are no products and there are users', function() {
				scope = {};
				controller = $controller('SearchCtrl', { $scope: scope, productsData: {count: 0, products: []}, query: QUERY, usersData: DUMMY_USERS});
				expect(productsTab(scope)).toBe(false);
				expect(userTab(scope)).toBe(true);
			});
			
			it('should show products tab if there are no products or users', function() {
				scope = {};
				controller = $controller('SearchCtrl', { $scope: scope, productsData: {count: 0, products: []}, query: 		QUERY, usersData: {count: 0, users: []}});
				expect(productsTab(scope)).toBe(true);
				expect(userTab(scope)).toBe(false);
			});
		});
		
		describe('$scope.categories', function() {
			it('should only have the categories of the products in results', function() {
				var presentCategories = ['gadget', 'game', 'music'];
				expect(scope.categories.map(function(a){ return a.name }).sort()).toEqual(presentCategories.sort());
			});
			
			it('should have all categories checked', function() {
				angular.forEach(scope.categories, function(c) {
					expect(c.checked).toBe(true);
                });
			});
		});
		
		// TODO: testear el update de searchHistory
    });
});