'use strict';
define(['productSeek', 'angular-slick-carousel', 'directives/productItem', 'services/restService', 'services/modalService'], function(productSeek) {

	productSeek.controller('ProductCtrl', ['authService', '$sce', 'restService', 'modalService', '$scope', '$location', 'product', function(auth, $sce, restService, modalService, $scope, $location, product) {

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

		$scope.parentCommentLengthError = false;

		$scope.childCommentLengthError = [];

		$scope.showParentSpinner = false;

		$scope.showChildSpinner = [];

		$.fn.goTo = function() {
	 		var offset = 100;
	        $('html, body').animate({
	            scrollTop: $(this).offset().top - offset + 'px'
	        }, 'fast');
	        return this; // for chaining...
	    };

	    function validChildCommentSubmit(index) {
			return $scope.childCommentForm[index].text.length < 512;
	    };

	    function validParentCommentSubmit(index) {
			return $scope.parentCommentForm.text.length < 512;
	    };

	    function childCommentSubmitNoError(parentCommentId, index) {
	    	$scope.showChildSpinner[index] = true;

	    	restService.commentParentProduct($scope.product.id, $scope.childCommentForm[index].text, parentCommentId).
			then(function(data) {
				$scope.showChildSpinner[index] = false;
				$scope.childCommentLengthError[index] = false;

				$scope.childCommentForm[index].text = '';
				$scope.comments[index].children.push(data);
			});

	    };

	   	function parentCommentSubmitNoError() {
			$scope.showParentSpinner = true;

			//scroll to bottom of page
			angular.element(document.getElementsByClassName('footer')).goTo();

			restService.commentProduct($scope.product.id, $scope.parentCommentForm.text).
			then(function(data) {
				$scope.showParentSpinner = false;
				$scope.parentCommentLengthError = false;

				$scope.parentCommentForm.text = '';
				$scope.comments.push(data);		
			});	
	    };

	    $scope.showReplyForm = function(target) {
			// Hide all other open comment forms.
			if ($scope.isLoggedIn) {
				$('.reply-comment').hide();
				
				var replyform = angular.element(target).closest('.comment-and-replies').find('.reply-comment');
				replyform.css('display', 'inline');
				
				replyform.goTo();
			}
		};

		$scope.parentCommentSubmit = function() {

			if (validParentCommentSubmit())
				parentCommentSubmitNoError();
			else
				$scope.parentCommentLengthError = true;
		};

		$scope.childCommentSubmit = function(parentCommentId, index) {
			
			if (validChildCommentSubmit(index))
				childCommentSubmitNoError(parentCommentId, index);
			else
				$scope.childCommentLengthError[index] = true;
		};

		$scope.trustedVideoUrl = function(video_id) {
            return $sce.trustAsResourceUrl('//www.youtube.com/embed/' + video_id + '?rel=0');
        };
        
        $scope.isOwnerLogged = function() {
            return $scope.isLoggedIn ? $scope.loggedUser.id === $scope.creator.id : false;
        };

		$scope.directToCreatorProfile = function() {
            $location.url('/profile/' + $scope.creator.id);
        };

        $scope.directToLoggedProfile = function() {
            $location.url('/profile/' + $scope.loggedUser.id);
        };

        $scope.directToAuthorProfile = function(id) {
            $location.url('/profile/' + id);
        };

        $scope.directToHome = function() {
        	$location.url('/');
        };

        $scope.signInModal = modalService.signInModal;
        $scope.signUpModal = modalService.signUpModal;
    }]);
});