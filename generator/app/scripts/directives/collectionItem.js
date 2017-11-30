'use strict';
define(['productSeek', 'services/authService', 'services/modalService','services/snackbarService' ,'controllers/DeleteCollectionModalCtrl', 'controllers/DeleteProductFromCollectionCtrl'], function(productSeek) {
    productSeek.directive('collectionItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: 'views/collectionItem.html',
            scope: {favList: '=', hideDelete: "="},
            controller: ['$scope', '$location','$route', 'authService', 'restService', 'modalService', 'snackbarService', function($scope, $location, $route, authService, restService, modalService, snackbarService) {                
                
                if($scope.hideDelete) {
                    $scope.collectionOffset = 12;
                } else {
                    $scope.collectionOffset = 11;
                }
                
                $scope.products = $scope.favList.products;
                $scope.directToProduct = function() {
                    $location.url('product/' + product.id);
                };

                var findIndexById = function(item, array) {
                    for (var i = 0; i < array.length; i++)
                        if (array[i].id === item.id)
                            return i;
                    
                    return -1;
                };
                
                var removeItemFrom = function(item, array) {
                    var idx = findIndexById(item, array);

                    if (idx != -1)
                        array.splice(idx, 1);
                };

                $scope.deleteProduct = function(product) {   
                    restService.deleteProductFromCollection($scope.favList.id, product.id)
                    .then(function() {
                    snackbarService.showSnackbar('ProductFormCollectionDeleted');
                    $uibModalInstance.close(true);
                    });
                };
                

                $scope.shouldExpand = function() {
                    // <c:set var="expand" value="${not empty favListRemovedId && favListRemovedId == favList.id}"></c:set>
                    
                }
                
                $scope.deleteCollection = function() {
                    var modal = modalService.deleteCollectionModal($scope.favList);
                    modal.result.then(function(isDeleted) {
                        if (isDeleted)
                            $scope.onDelete();
                    });
                }
            }]
        }
    });
});