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
        
        describe('.categories', function() {
            it('should have every category defined in the categories dependency and the category "all" alpha sorted', function() {
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

            it('should have every category not active given no category in $routeParams', function() {
                var $scope = {};
                var controller = buildController($scope);

                for (var i = 0; i < $scope.categories.length; i++) {
                    var c = $scope.categories[i];
                    
                    if (c.name !== 'all')
                        expect(c.active).toBe(false);
                }
            });
            
            it('should have the given category in $routeParams active and the rest not active', function() {
                for (var i = 0; i < categories.length; i++) {
                    var categoryName = categories[i];
                    var $scope = {};
                    var controller = buildController($scope, categoryName);
                    
                    for (var i = 0; i < $scope.categories.length; i++) {
                        var c = $scope.categories[i];
                        
                        if (c.name === categoryName)
                            expect(c.active).toBe(true);
                        else
                            expect(c.active).toBe(false);
                    }
                }
            });
        });
    });
});