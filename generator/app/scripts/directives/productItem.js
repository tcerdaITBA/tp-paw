'use strict';
define(['productSeek', 'controllers/DeleteModalCtrl'], function(productSeek) {
    productSeek.directive('productItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: '/views/productItem.html',
            scope: {product: '=', hideCategory: '=', hideDelete: '='},
            controller: ['$scope', '$location', '$uibModal', function($scope, $location, $uibModal) {
                var product = $scope.product;
                
                $scope.offset = $scope.hideCategory ? 6 : 3;
                
                $scope.directToCategory = function() {
                    $location.url('/?category=' + product.category);
                };
        
                $scope.directToProduct = function() {
                    $location.url('/product/' + product.id);
                };

                $scope.deleteProduct = function() {
                    console.log("Esto deberia borrar el producto");
                }


                $scope.deleteProduct = function() {
                    $scope.deleteModal = function() {
                        $uibModal.open({
                            templateUrl: 'views/modals/deleteModal.html',
                            controller: 'DeleteModalCtrl',
                            size: 'sm',
                            resolve: {
                                product: function() {
                                    return product;
                                }
                              }
                        });
                    }
                }

            }]
        }
    });
});