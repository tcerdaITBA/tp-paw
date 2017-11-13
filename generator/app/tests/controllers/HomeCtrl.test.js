define(['controllers/HomeCtrl', 'angular-mocks'], function() {
    describe('Home Controller', function() {
        var $controller, categories, sortCriterias, defaultSortCriteria;
        var DUMMY_PRODUCTS = {count: 2, 
                              products: [
                                  {id: '1', name: 'slime rancher', category: 'game'}, 
                                  {id: '2', name: 'apple watch', category: 'gadget'}
                              ]};
        
        beforeEach(module('productSeek'));
        
        beforeEach(inject(function(_$controller_, _categories_, _sortCriterias_, _defaultSortCriteria_) {
            $controller = _$controller_;
            categories = _categories_;
            sortCriterias = _sortCriterias_;
            defaultSortCriteria = _defaultSortCriteria_;
        }));

        var buildController = function($scope, routeParams) {
            var $routeParams = {};

            if (routeParams)
                $routeParams = routeParams;
            
            return $controller('HomeCtrl', {$scope: $scope, $routeParams: $routeParams, 
                productsData: DUMMY_PRODUCTS, categories: categories, sortCriteria: sortCriterias, defaultSortCriteria: defaultSortCriteria});
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
            it('should be defined', function() {
                var $scope = {};
                var controller = buildController($scope);

                expect($scope.categories).toBeDefined();
            });

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

                angular.forEach($scope.categories, function(c) {
                    expect(c.active).toBe(c.name === 'all');
                });
            });
            
            it('should have the given category in $routeParams active and the rest not active', function() {
                for (var i = 0; i < categories.length; i++) {
                    var categoryName = categories[i];
                    var $scope = {};
                    var controller = buildController($scope, {category: categoryName});
                    
                    angular.forEach($scope.categories, function(c) {
                        expect(c.active).toBe(c.name === categoryName);
                    });
                }
            });
        });

        describe('$scope.sortCriterias', function() {
            it('should be defined', function() {
                var $scope = {};
                var controller = buildController($scope);

                expect($scope.sortCriterias).toBeDefined();
            });

            it('should have every sortCriteria defined in the "sortCriteria" dependency', function() {
                var $scope = {};
                var controller = buildController($scope);

                for (var i = 0; i < $scope.sortCriterias.length; i++) {
                    expect(sortCriterias[i].orderBy).toEqual($scope.sortCriterias[i].orderBy);
                    expect(sortCriterias[i].order).toEqual($scope.sortCriterias[i].order);
                }
            });

            it('should have the default sortCriteria active given no order nor orderBy in $routeParams', function() {
                var $scope = {};
                var controller = buildController($scope);

                angular.forEach($scope.sortCriterias, function(sc) {
                    expect(sc.active).toBe(sc.orderBy === defaultSortCriteria.orderBy);
                });
            });

            it('should have the corresponding sortCriteria active given order and orderBy in $routeParams', function() {
                angular.forEach(sortCriterias, function(sortCriteria) {
                    var $scope = {};
                    var controller = buildController($scope, {order: sortCriteria.order, orderBy: sortCriteria.orderBy});

                    angular.forEach($scope.sortCriterias, function(sc) {
                        expect(sc.active).toBe(sc.order === sortCriteria.order && sc.orderBy === sortCriteria.orderBy);
                    });
                });              
            });
        });
        
        describe('$scope.setActiveCategory()', function() {
            it('should set the given category (or "all") to active and the others should not be active', function() {
                var $scope = {};
                var controller = buildController($scope);
                
                angular.forEach($scope.categories, function(category) {
                    $scope.setActiveCategory(category);
                    
                    angular.forEach($scope.categories, function(c) {
                         expect(c.active).toBe(category === c);
                    });
                });
            });
        });

        describe('$scope.setActiveSortCriteria()', function() {
            it('should set the given sort criteria to active and the others should no be active', function() {
                var $scope = {};
                var controller = buildController($scope);

                angular.forEach($scope.sortCriterias, function(sortCriteria) {
                    $scope.setActiveSortCriteria(sortCriteria);

                    angular.forEach($scope.sortCriterias, function(sc) {
                        expect(sc.active).toBe(sc === sortCriteria);
                    });
                });
            });
        });
    });
});