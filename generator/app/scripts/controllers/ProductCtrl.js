'use strict';
define(['productSeek', 'angular-slick-carousel'], function(productSeek) {

	productSeek.controller('ProductCtrl', ['authService', 'restService', '$scope', 'product', function(auth, restService, $scope, product) {
		$scope.product = product;
		$scope.description = product.description;
        $scope.creator = product.creator;
        $scope.video_ids = product.video_ids;
        $scope.image_urls = product.image_urls;
        $scope.comments = product.comments;
        $scope.index = 0;

        $scope.loggedUser = auth.getLoggedUser();
        $scope.isLoggedIn = auth.isLoggedIn();

        $scope.slickConfig = {
		  draggable: true,
		  enabled: true,
		  dots: true,
		  infinite: false,
		  slidesToScroll : 1,
		  slidesToShow: 1
		};

		$scope.parentComment = {};

		$scope.childComment = [];

		$scope.parentCommentSubmit = function() {
			restService.commentProduct($scope.product.id, $scope.parentComment.text);	
		};

		$scope.childCommentSubmit = function(parentCommentId, index) {
			$scope.index++;
			restService.commentParentProduct($scope.product.id, $scope.childComment[index].text, parentCommentId);	
		};
    }]);
});
