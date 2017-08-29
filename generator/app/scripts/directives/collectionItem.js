'use strict';
define(['productSeek'], function(productSeek) {
    productSeek.directive('collectionItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: '/views/collectionItem.html',
            scope: {favList: '='},
            controller: ['$scope', '$location', function($scope, $location) {
                console.log($scope.favList);
                $scope.directToProduct = function() {
                    $location.url('/product/' + product.id);
                };

                $scope.deleteProduct = function() {
                    console.log("Esto deberia borrar el producto");
                }

                $scope.shouldExpand = function() {
                    // <c:set var="expand" value="${not empty favListRemovedId && favListRemovedId == favList.id}"></c:set>
                    
                }

                $scope.favListLength = $scope.favList.length;

            }]
        }
    });
});