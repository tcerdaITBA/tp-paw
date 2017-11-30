'use strict';
define(['productSeek'], function(productSeek) {
    productSeek.directive('collectionItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: 'views/collectionItem.html',
            scope: {favList: '='},
            controller: ['$scope', '$location', function($scope, $location) {
                $scope.collectionOffset = 12; // TODO: si el usuario está loggeado y es su colección vale 11
                $scope.products = $scope.favList.products;
                $scope.directToProduct = function() {
                    $location.url('product/' + product.id);
                };

                $scope.deleteProduct = function() {
                    console.log("Esto deberia borrar el producto");
                }

                $scope.shouldExpand = function() {
                    // <c:set var="expand" value="${not empty favListRemovedId && favListRemovedId == favList.id}"></c:set>
                    
                }
            }]
        }
    });
});