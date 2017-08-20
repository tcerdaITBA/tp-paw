define(['controllers/HomeCtrl', 'angular-mocks'], function() {
    describe('Home Controller', function() {
        var $controller, categories;
        var DUMMY_PRODUCTS = {count: 2, 
                              products: [
                                  {id: '1', name: 'slime rancher', category: 'game'}, 
                                  {id: '2', name: 'apple watch', category: 'gadget'}
                              ]};
        
        beforeEach(module('productSeek'));
        
        beforeEach(inject(function(_$controller_, _categories_) {
            $controller = _$controller_;
            categories = _categories_;
        }));

        var buildController = function($scope, category) {
            var $routeParams = {}
            if (category)
                $routeParams.category = category;
            
            return $controller('HomeCtrl', {$scope: $scope, $routeParams: $routeParams, productsData: DUMMY_PRODUCTS, categories: categories});
        };
        
        var findCategory = function(categories, name) {
            for (var i = 0; i < categories.length; i++)
                if (categories[i].name === name)
                    return categories[i];
        };
        
        describe('$scope.products', function() {
            it('should be defined with the resolved "productsData" products', function() {
                var $scope = {};
                var controller = buildController($scope);
                
                expect($scope.products).toEqual(DUMMY_PRODUCTS.products);
            });
        });
        
        describe('$scope.categories', function() {
            it('should have every category defined in the "categories" dependency and the category "all" preppended and alpha sorted', function() {
                var $scope = {};
                var controller = buildController($scope);
                var expectedNames = ['all'].concat(categories);
                
                for (var i = 0; i < $scope.categories.length; i++)
                    expect($scope.categories[i].name).toBe(expectedNames[i]);
            });
            
            it('should have "all" active given no category in $routeParams', function() {
                var $scope = {};
                var controller = buildController($scope);
                var all = findCategory($scope.categories, 'all');
                
                expect(all.active).toBe(true);
            });

            it('should have every category (except "all") not active given no category in $routeParams', function() {
                var $scope = {};
                var controller = buildController($scope);

                angular.forEach($scope.categories, function(c) {
                    if (c.name !== 'all')
                        expect(c.active).toBe(false);
                });
            });
            
            it('should have the given category in $routeParams active and the rest not active', function() {
                for (var i = 0; i < categories.length; i++) {
                    var categoryName = categories[i];
                    var $scope = {};
                    var controller = buildController($scope, categoryName);
                    
                    angular.forEach($scope.categories, function(c) {
                        c.name === categoryName ? expect(c.active).toBe(true) : expect(c.active).toBe(false);
                    });
                }
            });
        });
        
        describe('$scope.setActiveCategory()', function() {
            it('should set the given category (or "all") to active and the others should not be active', function() {
                var $scope = {};
                var controller = buildController($scope);
                
                angular.forEach($scope.categories, function(category) {
                    $scope.setActiveCategory(category);
                    
                    angular.forEach($scope.categories, function(c) {
                        category === c ? expect(c.active).toBe(true) : expect(c.active).toBe(false);
                    });
                });
            });
        });
    });
});