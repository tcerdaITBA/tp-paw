'use strict';
define(['productSeek'], function(productSeek) {
    productSeek.directive('productItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: '/views/productItem.html',
            scope: {product: '=', hideCategory: '=', hideDelete: '='},
            controller: ['$scope', '$location', function($scope, $location) {
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


            }]
        }
    });
});