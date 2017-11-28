'use strict';
define([], function() {

	return {
        app: 'Apps',
        art: 'Art',
        book: 'Books',
        fashion: 'Fashion',
        film: 'Films',
        food: 'Food',
        gadget: 'Gadgets',
        game: 'Games',
        music: 'Music',
        other: 'Other',
        all: 'All',
        
        'date': 'Recent',
        'alpha': 'a-z',
        'votes': 'Most Voted',
        
        'description.app': 'Here you will find the best apps for all your needs.',
        'description.art': 'If you are seeking inspiration, this is the right place to come. Take a moment to let your emotions flow.',
        'description.book': 'There is nothing like a good book. Expand your knowledge or just feel the excitement of a great adventure.',
        'description.fashion': 'Want to know about the latest fashion and trends? Then this is the category you\u2019re looking for.',
        'description.film': 'Here you will find movies, shoots and much more! You won\u2019t be disappointed, we promise.',
        'description.food': 'Want to try something new? Here you will find outstanding dishes that will overwhelm you.',
        'description.gadget': 'Here you can find practical tools that incorporate cutting-edge technology. You can\u2019t miss this!',
        'description.game': 'Find something new and exciting to play at home or on the go. Work can wait. Have some fun.',
        'description.music': 'Having a tough day? It\u2019s time to listen the music that makes you feel like a boss.',
        'description.other': 'Can\u2019t find a category that matches the product you seek? You may find it here.',
        'description.all': 'Here you will find all the products of any category.',
		
	'categoryZRP.sorry': '\uD83D\uDE14',
	'categoryZRP.noProducts': 'There are no products in this category yet.',
	
	'navbar.mostPopular':'Most Popular',
	'navbar.signup':'Sign Up',
	'navbar.signin':'Sign In',
	'navbar.post':'Post Product',
	'navbar.logout':'Logout',
	'navbar.search':'Search',
	'navbar.searchHistory': 'Search history',
	
	'modal.signIn.signToAccount':'Sign in to your account',
	'modal.signIn.usernamePlaceholder':'hello@productseek.com',
	'modal.signIn.passwordPlaceholder':'Password',
	'modal.signIn.rememberMe':'Remember me',
	'modal.signIn.signIn':'Sign In',
        'modal.signIn.error.invalidUser': 'Invalid email or password',

	'searchResults.products':'Products',
	'searchResults.users':'Users',
	'searchResults.resultsFor': 'Search results for ',
	'searchResults.filterByCategory': 'Filters',
	'searchResults.resetFilters': 'Reset filters',
	
	'searchZRP.notFound':'\uD83D\uDD0E',
	'searchZRP.tryDifferentSearch':'Try a different search',
	'searchZRP.noUsers':'No users found containing {{keyword}}',
	'searchZRP.noProducts':'No products found containing {{keyword}}',
		
        mostRecent: 'Most recent products',
        allProducts: 'All',
        recent: 'Recent',
        popular: 'Most voted',
        atoz: 'a-z',

        'Profile.Tab.uploadedProducts': 'Uploaded',
        'Profile.Tab.votedProducts': 'Voted',
        'Profile.Tab.favlist': 'Collections',
        'Profile.settings.changePicture':'Change profile picture',
        'Profile.settings.changePassword': 'Change password',
        'userFormLabel.passwordConfirm': 'Confirm Password',
    
        'changePassword.currentPasswordConf': 'Current Password',
        'changePassword.newPassword':'New Password',
        'changePassword': 'Change Password',
        'passwordForm.cancel': 'Cancel',

        'userFormLabel.newProfilePicture': 'New Profile Picture',

        'Profile.modal.deleteProduct': 'Delete Product',
        'Profile.modal.textBeginning': 'Are you sure you want to delete',
        'Profile.modal.textEnd': 'This action can\u2019t be undone.',
        'Profile.modal.rightButton': 'Cancel',
        'Profile.modal.leftButton': 'Delete',    
		
        'collection.collectionNamePlaceholder':'Name',
        'collections.newCollection':'New Collection',
        'collections.add':'Add',
        'collections.toCollection':'to Collection',
        'collections.noCollectionsYet':'No Collections created yet.',
        'collections.createAndAdd':'Create Collection and add',
        'collections.addInNewCollection':'Add to new Collection',
        'collections.createCollection':'Create new Collection',
        'collections.create':'Create',
        'collections.alreadyInCollection':'Already in this Collection',
        
        'ownerUserZRP.noFavLists': 'You have not created any Collection yet.',
        
	'post.postProduct': 'Post product',
	'post.giveUsInformation': 'Give us some information about your product',
	'post.productName': 'Product name',
	'post.placeholder.productName': 'Product Seek',
	'post.tagline': 'Tagline',
	'post.placeholder.tagline': 'A quick description of your product',
	'post.description': 'Description',
	'post.placeholder.description': 'Complete description',
	'post.webpageOptional': 'Web Page (Optional)',
	'post.placeholder.webpage': 'www.productseek.com',
	'post.category': 'Category',
	'post.addImageOrVideo': 'Add at least an image or video',
        'post.videos': 'Youtube Videos',
	'post.link': 'Link',
	'post.postButton': 'Post',
	
	'post.error.addImageOrVideo': 'You should add an image or Youtube video',
	'post.error.invalidYoutube': 'Not a valid Youtube link',
	'post.error.addTagline': 'Add a tagline',
        'post.error.taglineTooLong': 'Tagline cannot exceed 140 characters',
	'post.error.addName': 'Add a product name',
        'post.error.nameTooShort': 'Product name must be at least 4 characters long',
        'post.error.nameTooLong': 'Product name cannot exceed 64 characters',
	'post.error.addDescription': 'Add a description',
        'post.error.descriptionTooLong': 'Description cannot exceed 8000 characters',
        'post.error.addLogo': 'Add a logo',
        'post.error.invalidWebpage': 'Invalid webpage',
		
        'button.addLogo': 'Add Logo',
	'button.addImage':'Add Image',
		
        'product.singular': 'product',
        'product.plural': 'products',
        
        'collection.error.nameTooShort': 'Collection name should have at least 4 characters',
        'collection.error.nameTooLong': 'Collection name cannot exceed 64 characters',
        
        'feedback.favListCreated.textEnd': 'was created',
        'feedback.productAddedToFavList.text': 'was added to',
        'feedback.productDeleted.textEnd': 'was deleted',

        'feedback.passwordChanged.text': 'Password changed successfully',
        'feedback.pictureChanged.text': 'Profile picture changed successfully',

        'feedback.warning.noConnection': 'Oops, looks like you lost connection',
        

        'button.addProfilePicture' : 'Profile Picture',
        'modal.signUp.title': 'Sign Up',
        'modal.signUp.passwordConfPlaceholder': 'Confirm Password',
        'modal.signUp.usernamePlaceholder': 'Name',
        'modal.signUp.emailPlaceholder': 'Email',
        'modal.signUp.signUp': 'Sign Up',
        
        'modal.password.notMatchOldPassword' : 'Password does not match the current password',

        'signUp.error.addPicture': 'Add profile picture',
        'signUp.error.addName': 'Add name',
        'signUp.error.nameTooLong': 'Name cannot exceed 30 characters',
        'signUp.error.nameTooShort': 'Name must be at least 4 characters long',
        'signUp.error.addPassword': 'Add password',
        'signUp.error.passwordTooLong': 'Password cannot exceed 60 characters',
        'signUp.error.passwordTooShort': 'Password must be at least 6 characters long',
        'signUp.error.passwordMatch': 'Passwords do not match',
        'signUp.error.addEmail': 'Add email',
        'signUp.error.invalidEmail': 'Invalid email',
	'signIn.error.addEmail': 'Insert your email',
	'signIn.error.addPassword': 'Insert your password',

        'modal.changePicture.empty': 'Add image',

        'product.creator':'Creator',
        'product.comment':'Comment',
        'product.comment.placeholder':'Write your comment here',
        'product.comment.error':'Comment length must be lower or equal than 512',
        'product.discussion':'Join discussion',
        'product.anonymousComment.beginning':'In order to comment a product you need to',
        'product.anonymousComment.signIn':'sign in',
        'product.anonymousComment.or':'or',
        'product.anonymousComment.signUp':'sign up.',
        'product.reply':'Reply'

	};
});
