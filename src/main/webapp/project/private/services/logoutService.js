/**
* Arquivo service Logout em angular.
* @author Samael Pereira Simões
*/
app.factory('LogoutService', function($resource) {
	return $resource('/control-expenses/LogoutServlet', null, {
		
			method: 'GET'
		
	});
});