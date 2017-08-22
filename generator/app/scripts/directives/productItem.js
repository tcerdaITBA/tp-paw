'use strict';
define(['productSeek', 'services/authService'], function(productSeek) {
    productSeek.directive('productItem', function() {
        return {
            restrict: 'E',
            replace: 'true',
            templateUrl: '/views/productItem.html',
            scope: {product: '=', hideCategory: '='},
            controller: ['$scope', '$location', 'authService', 'restService', function($scope, $location, authService, restService) {
                var product = $scope.product;
                
                $scope.offset = $scope.hideCategory ? 6 : 3;
                
                $scope.directToCategory = function() {
                    $location.url('/?category=' + product.category);
                };
        
                $scope.directToProduct = function() {
                    $location.url('/product/' + product.id);
                };
                
                $scope.isLoggedIn = authService.isLoggedIn;
                
                if ($scope.isLoggedIn()) {
                    $scope.toggleVote = function() {
                        $scope.product.voted = !$scope.product.voted;
                        
                        if ($scope.product.voted) {
                            $scope.product.voters_count += 1;
                            restService.voteProduct(product.id);
                        }
                        else {
                            $scope.product.voter_count -= 1;
                            restService.unvoteProduct(product.id);
                        }
                    }
                }
            }]
        }
    });
});