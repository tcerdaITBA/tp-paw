define(['productSeek', 'services/restService'], function(productSeek) {

    'use strict';
    productSeek.controller('CollectionModalCtrl', ['$scope', 'product', 'collections', 'restService', function($scope, product, collections, restService) {
        $scope.product = product;
        $scope.collections = collections;
        $scope.emptyCollection = collections.length == 0;
        $scope.showWell = false;

        console.log(collections);
        
        $scope.addToCollection = function(collection) {
            collection.push(product);
            restService.addProductToCollection(product.id, collection.id);
            // TODO: feedback
        };
        
        // TODO: manejo de errores
        $scope.createAndAdd = function() {
            if ($scope.collectionName) {
                restService.createCollection($scope.collectionName)
                .then(function(data) {
                    $scope.collections.push(data);
                    return restService.addProductToCollection(product);
                })
                .then(function(data){
                    len = $scope.collections.lenght;
                    $scope.collections[len - 1].push(product);
                });
            }
        };
        
        $scope.toggleWell = function() {
            $scope.showWell = !$scope.showWell;
        };
    }]);

});