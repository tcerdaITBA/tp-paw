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

		$scope.parentCommentForm = {};

		$scope.childCommentForm = [];

		$scope.showParentSpinner = {};

		$scope.showChildSpinner = [];

		$.fn.goTo = function() {
	 		var offset = 100;
	        $('html, body').animate({
	            scrollTop: $(this).offset().top - offset + 'px'
	        }, 'fast');
	        return this; // for chaining...
	    };

	    $scope.showReplyForm = function(target) {
			// Hide all other open comment forms.
			$('.reply-comment').hide();
			
			var replyform = angular.element(target).closest('.comment-and-replies').find('.reply-comment');
			replyform.css('display', 'inline');
			
			replyform.goTo();
		};

		$scope.parentCommentSubmit = function() {
			$scope.showParentSpinner = true;

			//scroll to bottom of page
			angular.element(document.getElementsByClassName('footer')).goTo();

			restService.commentProduct($scope.product.id, $scope.parentCommentForm.text).
			then(function(data) {
				//sleep(2000);
				$scope.showParentSpinner = false;

				$scope.parentCommentForm.text = '';
				$scope.comments.push(data);		
			});	
		};

		$scope.childCommentSubmit = function(parentCommentId, index) {
			$scope.showChildSpinner[index] = true;

			restService.commentParentProduct($scope.product.id, $scope.childCommentForm[index].text, parentCommentId).
			then(function(data) {
				//sleep(2000);
				$scope.showChildSpinner[index] = false;

				$scope.childCommentForm[index].text = '';
				$scope.comments[index].children.push(data);
			});
		};

		// debugging
		// function sleep(milliseconds) {
		//   var start = new Date().getTime();
		//   for (var i = 0; i < 1e7; i++) {
		//     if ((new Date().getTime() - start) > milliseconds){
		//       break;
		//     }
		//   }
		// }
    }]);
});