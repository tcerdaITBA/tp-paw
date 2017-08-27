'use strict';
define(['productSeek', 'services/authService', 'services/modalService', 'services/restService', 'directives/loginRequiredPopover'], function(productSeek) {
    productSeek.directive('productItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: '/views/productItem.html',
            scope: {product: '=', hideCategory: '='},
            controller: ['$scope', '$location', '$route', 'authService', 'modalService', 'restService', function($scope, $location, $route, authService, modalService, restService) {
                var product = $scope.product;
                
                $scope.offset = $scope.hideCategory ? 6 : 3;
                
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
                }
                
                $scope.signInModal = modalService.signInModal;
                $scope.signUpModal = modalService.signUpModal;
            }]
        }
    });
});