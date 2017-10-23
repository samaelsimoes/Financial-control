/**
* Arquivo Logout em angular.
* @author Samael Pereira Simões
*/

app.controller('LogoutController', ['$scope', '$rootScope', '$location', 'toastr', 'LogoutService',
								  function( $scope,  $rootScope,  $location, toastr, LogoutService) {
	
	$rootScope.activetab = $location.path();
	user={
		acao:"logout",
		idlogout:1
	};
	$scope.reload = function(  ) {		
		LogoutService.query( user, function(success) {	
			toastr.info("Saindo do sistema");
			
			window.setTimeout(function() {
				location.reload();
				//$(location).attr('href', '/control-expenses/#!/login');			
		    }, 2000);
			
		}, function(err){
			toastr.warning(err.data.msg);
		})
	}
}]);

