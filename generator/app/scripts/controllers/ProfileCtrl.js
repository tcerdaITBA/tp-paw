'use strict';
define(['productSeek'], function(productSeek) {

	productSeek.controller('ProfileCtrl', function($scope, user, collections, createdProducts, votedProducts) {
		$scope.user = user;
		$scope.collections = collections;
		$scope.createdProducts = createdProducts;
		$scope.votedProducts = votedProducts;

		var isEmpty = function(set) {
			console.log(set);
			return set.count == 0;
			
		}	
	
		$scope.firstActiveTab = function() {
			if(!isEmpty(collections))
				return 1;
			else if(!isEmpty(createdProducts))
				return 2;
			else if(!isEmpty(votedProducts))
				return 3;
			else
				return 1;
		}
	
	});
});
