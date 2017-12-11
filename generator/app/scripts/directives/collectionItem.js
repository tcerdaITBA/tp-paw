'use strict';
define(['productSeek', 'services/authService', 'services/modalService','services/snackbarService' ,'controllers/DeleteCollectionModalCtrl', 'controllers/DeleteProductFromCollectionCtrl'], function(productSeek) {
    productSeek.directive('collectionItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: 'views/collectionItem.html',
            scope: {favList: '=', hideDelete: "=", onDelete: "&"},
            controller: ['$scope', '$location','$route', 'authService', 'restService', 'modalService', 'snackbarService', function($scope, $location, $route, authService, restService, modalService, snackbarService) {                
                
                if($scope.hideDelete) {
                    $scope.collectionOffset = 12;
                } else {
                    $scope.collectionOffset = 11;
                }
                
                // toggle manual pues href con # redirecciona al abrir modal de eliminar coleccion debido al stopPropagation 
                $scope.collapse = function() {
                    $('#collapse-' + $scope.favList.id).collapse('toggle');
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

                $scope.removeProduct = function(product) {
                    var idx = findIndexById(product, $scope.products);
                    removeItemFrom(product, $scope.products);

                    restService.removeProductFromCollection($scope.favList.id, product.id)
                    .then(function() {
                        $scope.product = product;
                        snackbarService.showSnackbar('ProductRemoved' + $scope.favList.id);
                    })
                    .catch(function(data) {
                        snackbarService.showNoConnection();
                        $scope.products.splice(idx, 0, product); // reinsert item into collection
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