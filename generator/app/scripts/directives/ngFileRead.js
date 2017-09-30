'use strict';
define(['productSeek'], function(productSeek) {
    productSeek.directive('ngFileRead', function($parse) {
        return {
            link: function(scope, element, attrs) {
				element.bind("change", function (changeEvent) {
					var reader = new FileReader();
					var handler = $parse(attrs.ngFileRead);
					reader.onload = function (loadEvent) {
						scope.$apply(function () {
							handler(scope, {data: loadEvent.target.result});
						});
					};
					reader.readAsDataURL(changeEvent.target.files[0]);
				});
            }
        }
    });
});