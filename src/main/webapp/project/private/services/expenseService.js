/**
 * Arquivo service Despesas em angular.
 * @author Samael Pereira Simões
 */

app.factory('ExpenseService', function($resource) {
	return $resource('/control-expenses/rest/expense/', null, {
		update: {
			method: 'PUT'
		},
		remove: {
			method: 'DELETE'
		},
  	});
});