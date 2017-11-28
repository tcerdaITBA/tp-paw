'use strict';
define(['productSeek'], function(productSeek) {
    productSeek.directive('collectionItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: 'views/collectionItem.html',
            scope: {favList: '=', hideDelete: "="},
            controller: ['$scope', '$location', function($scope, $location) {                
                
                if($scope.hideDelete) {
                    $scope.collectionOffset = 12;
                } else {
                    $scope.collectionOffset = 11;
                }
                
                $scope.products = $scope.favList.products;
                $scope.directToProduct = function() {
                    $location.url('/product/' + product.id);
                };

                $scope.deleteProduct = function() {
                    console.log("Esto deberia borrar el producto");
                }

                $scope.shouldExpand = function() {
                    // <c:set var="expand" value="${not empty favListRemovedId && favListRemovedId == favList.id}"></c:set>
                    
                }
                
                $scope.deleteCollection = function() {
                    console.log("Esto deberia borrar la coleccion");
                }
            }]
        }
    });
});