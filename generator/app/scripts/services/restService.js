'use strict';
define(['productSeek', 'jquery', 'services/sessionService'], function(productSeek) {
		return productSeek.factory('restService', ['$http', '$q', 'url', 'sessionService', function($http, $q, url, session) {
            
            var translateTable = {
                    category: 'category',
                    page: 'page',
                    pageSize: 'per_page',
                    orderBy: 'sorted_by',
                    order: 'order',
                    query: 'q'
            };
            
            function dataURItoBlob(dataURI) {
              // convert base64 to raw binary data held in a string
              var byteString = atob(dataURI.split(',')[1]);

              // separate out the mime component
              var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

              // write the bytes of the string to an ArrayBuffer
              var ab = new ArrayBuffer(byteString.length);

              // create a view into the buffer
              var ia = new Uint8Array(ab);

              // set the bytes of the buffer to the correct values
              for (var i = 0; i < byteString.length; i++) {
                  ia[i] = byteString.charCodeAt(i);
              }

              // write the ArrayBuffer to a blob, and you're done
              var blob = new Blob([ab], {type: mimeString});
              return blob;

            }

            function translate(params) {
                var translated = {};
                
                if (params) {
                    jQuery.each(params, function(key, value) {
                        translated[translateTable[key]] = value;
                    });
                }
                
                return translated;
            }
			
            function authHeaders() {
                var accessToken = session.getAccessToken();
                return accessToken ? {headers: {'X-AUTH-TOKEN': accessToken}} : undefined;
            }
            
            function multipartMetadata() {
                var accessToken = session.getAccessToken();
                var metadata = {
                    transformRequest: angular.identity,
                    headers: {
                        'Content-Type': undefined
                    }
                };
                
                if (accessToken)
                    metadata.headers['X-AUTH-TOKEN'] = session.getAccessToken();
                
                return metadata;
            }
            
            function doPost(baseUrl, data, params) {
            	var params = translate(params);
 				params = Object.keys(params).length ? '?' + jQuery.param(params) : '';

                return $http.post(baseUrl + params, JSON.stringify(data), authHeaders())
                        .then(function(response) {
                            return response.data;
                        })
                        .catch(function(response) {
                            console.log(response.data);
                            return $q.reject(response.data);
                        });   
            }
            
			function doGet(baseUrl, params) {
                var params = translate(params);
				params = Object.keys(params).length ? '?' + jQuery.param(params) : '';
                
				return  $http.get(baseUrl + params, authHeaders())
						.then(function(response) {
							return response.data;
						})
						.catch(function(response) {
							return $q.reject(response.data);
						});
			}
            
            function doPut(baseUrl, data, params) {
                var params = translate(params);
				params = Object.keys(params).length ? '?' + jQuery.param(params) : '';

                return $http.put(baseUrl + params, JSON.stringify(data), authHeaders())
                        .then(function(response) {
                            return response.data;
                        })
                        .catch(function(response) {
                            return $q.reject(response.data);
                        });
            }

            function doDelete(baseUrl, params) {
                var params = translate(params);
				params = Object.keys(params).length ? '?' + jQuery.param(params) : '';

                return $http.delete(baseUrl + params, authHeaders())
                        .then(function(response) {
                            return response.data;
                        })
                        .catch(function(response) {
                            return $q.reject(response.data);
                        });
            }            
            
			return {
				getProducts: function(params) {
					return doGet(url + '/products', params);
				},
                
				getProduct: function(id) {
					return doGet(url + '/products/' + id);
				},
                
				getComments: function(id, params) {
					return doGet(url + '/products/' + id + '/comments', params);
				},
                
				getCollectionsForUser: function(id, params) {
					return doGet(url + '/users/' + id + '/collections', params);
				},
                
				getUser: function(id) {
					return doGet(url + '/users/' + id);
				},
                
				getVotedByUser: function(id, params) {
					return doGet(url + '/users/' + id + '/voted_products', params);
				},
                
				getPostedByUser: function(id, params) {
					return doGet(url + '/users/' + id + '/created_products', params);
				},
                
				getProductVoters: function(id, params) {
					return doGet(url + '/product/' + id + '/voters', params);
				},
                
				searchProducts: function(query) {
					return doGet(url + '/search/products', {query: query});
				},
                
				searchUsers: function(query) {
					return doGet(url + '/search/users', {query: query});
				},
                
                voteProduct: function(id) {
                    return doPut(url + '/products/' + id + '/votes');
                },

                addProductToCollection: function(productId, collectionId) {
                    return doPut(url + '/collections/' + collectionId + '/products/' + productId);
                },
                
                deleteCollection: function(id) {
                    return doDelete(url + '/collections/' + id);
                },
                
                deleteProductFromCollection: function(collectionId, productId) {
                    return doDelete(url + '/collections/' + collectionId + '/products/' + productId);
                },
                
                unvoteProduct: function(id) {
                    return doDelete(url + '/products/' + id + '/votes');
                },
                
                deleteProduct: function(id) {
                    return doDelete(url + '/products/' + id);
                },
                
                createCollection: function(name) {
                    return doPost(url + '/collections', {'name': name});
                },

                changePassword: function(currentPass, newPass) {
                    return doPut(url + '/user/password', 
                            {'current_password': currentPass, 'new_password': newPass});
                },
                
                changeProfilePicture: function(data){
                    var picture = data.picture;
                    var formData = new FormData();

                    formData.append('picture', dataURItoBlob(picture));
                    return $http.put(url + '/user/picture', formData, multipartMetadata())
                    .then(function(response){
                        return response.data;
                    })
                    .catch(function(response){
                        return $q.reject(response.data);
                    });
                },

                createUser: function(data) {
                    var userData = {name: data.name, password: data.password, email: data.email};
                    var picture = data.picture;
                    var formData = new FormData();
                    
                    formData.append('picture', dataURItoBlob(picture));
                    formData.append('user', new Blob([JSON.stringify(userData)], {type: "application/json"}));
                    
                    return $http.post(url + '/users', formData, multipartMetadata())
                    .then(function(response) {
                        return response.data;
                    })
                    .catch(function(response) {
                        return $q.reject(response.data);
                    });
                },
                
                postProduct: function(data) {
                    var productData = {name: data.name, tagline: data.tagline, description: data.description, website: data.website, category: data.category, video_ids: data.videoIds};
                    var logo = data.logo;
                    var images = data.images;
                    var formData = new FormData();
                    
                    angular.forEach(images, function(img) {
                        if (img)
                            formData.append('picture', dataURItoBlob(img));
                    });
                                        
                    formData.append('logo', dataURItoBlob(logo));
                    formData.append('product', new Blob([JSON.stringify(productData)], {type: "application/json"}));
                    
                    return $http.post(url + '/products', formData, multipartMetadata())
                    .then(function(response) {
                        return response.data;
                    })
                    .catch(function(response) {
                        console.log(response.data);
                        return $q.reject(response.data);
                    });
                },

                commentProduct: function(id, comment) {
                    return doPost(url + '/products/' + id + '/comments', {content: comment})
                },
                  
                commentParentProduct: function(id, comment, parentCommentId) {
                    return doPost(url + '/products/' + id + '/comments', {content: comment, parent_id: parentCommentId})
				}
			}
		}]);
	}
);

