'use strict';
define(['productSeek', 'angular-slick-carousel'], function(productSeek) {

	productSeek.controller('ProductCtrl', ['authService', 'restService', '$scope', 'product', function(auth, restService, $scope, product) {
		$scope.product = product;
		$scope.description = product.description;
        $scope.creator = product.creator;
        $scope.video_ids = product.video_ids;
        $scope.image_urls = product.image_urls;
        $scope.comments = product.comments;

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

		$scope.commentForm = {};
		$scope.commentForm.comment = {};

		$scope.commentSubmit = function() {
			restService.commentProduct($scope.product.id, $scope.commentForm.comment.text);	
		};
    }]);
});
