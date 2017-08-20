'use strict';
define(['productSeek'], function(productSeek) {

	productSeek.controller('ProductCtrl', ['$scope', 'product', function($scope, product) {
		$scope.product = product;
        $scope.creator = product.creator;
        $scope.comments = product.comments;
    }]);
});
