define(['productSeek', 'services/restService', 'services/snackbarService', 'directives/snackbar'], function(productSeek) {

    'use strict';
    productSeek.controller('CollectionModalCtrl', ['$scope', '$uibModalInstance', 'product', 'collections', 'restService', 'snackbarService', function($scope, $uibModalInstance, product, collectionObject, restService, snackbarService) {
        $scope.product = product;
        $scope.collections = collectionObject.collections;
        $scope.emptyCollections = $scope.collections.length == 0;
        $scope.showWell = false;

        var collectionContainsProduct = function(collection) {
            for (var i = 0; i < collection.products.length && collection.containsProduct === undefined; i++)
                if (collection.products[i].id === $scope.product.id)
                    collection.containsProduct = true;
            
            if (!collection.containsProduct)
                collection.containsProduct = false;
            
            return collection.containsProduct;
        };
        
        for (var i = 0; i < $scope.collections.length; i++)
            collectionContainsProduct($scope.collections[i]);
                
        $scope.dismiss = function() {
            $uibModalInstance.dismiss('close');            
        };
        
        $scope.addToCollection = function(collection) {
            collection.products.push(collection);
            collection.count = collection.products.length;
            collection.containsProduct = true;
            restService.addProductToCollection(product.id, collection.id);
            
            snackbarService.showSnackbar('productAdded');
            $scope.collectionAddedTo = collection.name;
            $scope.dismiss();
        };
        
        $scope.createAndAdd = function() {
            if ($scope.collectionName) {
                restService.createCollection($scope.collectionName)
                .then(function(data) {
                    $scope.collections.push(data);
                    return restService.addProductToCollection(product.id, data.id);
                })
                .then(function(data) {
                    var len = $scope.collections.length;
                    var coll = $scope.collections[len - 1];
                    $scope.toggleWell();  // close well
                    coll.products.push(product);
                    coll.count = 1;
                    coll.containsProduct = true;
                    
                    snackbarService.showSnackbars(['collectionCreated', 'productAdded']);
                    $scope.collectionAddedTo = coll.name;
                    $scope.dismiss();
                });
            }
        };
        
        $scope.toggleWell = function() {
            $scope.showWell = !$scope.showWell;
        };
    }]);

});