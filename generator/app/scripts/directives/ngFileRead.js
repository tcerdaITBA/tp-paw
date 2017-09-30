'use strict';
define(['productSeek'], function(productSeek) {
    productSeek.directive('ngFileRead', function() {
        return {
			scope: {
				ngFileRead: '='
			},
            link: function(scope, element, attrs) {
				element.bind("change", function (changeEvent) {
					var reader = new FileReader();
					reader.onload = function (loadEvent) {
						scope.$apply(function () {
							scope.ngFileRead = loadEvent.target.result;
						});
					};
					reader.readAsDataURL(changeEvent.target.files[0]);
				});
            }
        }
    });
});