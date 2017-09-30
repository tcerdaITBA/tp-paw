'use strict';
define(['productSeek', 'services/authService', 'services/modalService', 'services/sessionService', 'controllers/DeleteModalCtrl', 'controllers/CollectionModalCtrl'], function(productSeek) {
    productSeek.directive('productItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: '/views/productItem.html',
            scope: {product: '=', hideCategory: '=', hideDelete: '=', onDelete: '&'},
            controller: ['$scope', '$location', '$route', 'authService', 'restService', 'modalService', function($scope, $location, $route, authService, restService, modalService, sessionService) {

                var product = $scope.product;
                
                $scope.offset = $scope.hideCategory ? 6 : 3;
                $scope.loggedUser = authService.loggedUser;
                
                $scope.directToCategory = function() {
                    $location.url('/?category=' + product.category);
                };
        
                $scope.directToProduct = function() {
                    $location.url('/product/' + product.id);
                };
                
                $scope.isLoggedIn = authService.isLoggedIn();
                
                $scope.$on('user:updated', function() {
                    if (!authService.isLoggedIn()) {
                        $scope.product.voted = false;
                        $scope.isLoggedIn = false;
                        $scope.loggedUser = null;
                    }
                    else
                        $route.reload(); // Must retrieve product again in order to know whether it was voted by the new user or not
                });
                
                $scope.toggleVote = function() {
                    if ($scope.isLoggedIn) {
                        $scope.product.voted = !$scope.product.voted;

                        if ($scope.product.voted) {
                            $scope.product.voters_count += 1;
                            restService.voteProduct(product.id);
                        }
                        else {
                            $scope.product.voters_count -= 1;
                            restService.unvoteProduct(product.id);
                        }
                    }
                };
                
                $scope.showCollectionModal = function() {
                    modalService.collectionModal($scope.product, authService.loggedUser.collections);
                };
                
                $scope.deleteModal = function() {
                    var modal = modalService.deleteModal($scope.product);
                    console.log(modal);
                    console.log(modal.results);
                    modal.result.then(function(isDeleted) {
                        if (isDeleted)
                            $scope.onDelete();
                    });
                };
            }]
        }
    });
});