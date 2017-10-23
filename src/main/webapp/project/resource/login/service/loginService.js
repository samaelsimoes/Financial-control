
/**
* Arquivo service Login em angular.
* @author Samael Pereira Simões
*/
app.factory('LoginService', function($resource) {
	return $resource('/control-expenses/LoginServlet', null, {
		query:{
			method: 'GET'
		}
	});
});