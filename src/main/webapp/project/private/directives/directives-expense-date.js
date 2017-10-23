/**
 * Arquivo contendo diretivas dos componentes em angular.
 * @author Samael Pereira Simões
 */

/**
 * Diretiva de atributo para definir o datepicker.
*/

app.directive('datepicker', ['$timeout', function($timeout) {
	
	return {
		
		restrict: 'AC',
		require: "ngModel",
		
		link: function($scope, element, attrs, ngModelCtrl) {
			var updateModel = function(date) {
				
				$scope.$apply(function () {
					
					ngModelCtrl.$setViewValue(date);//pegando o valor da data ?
		        });
			};
			
			$timeout(function() {
				
				$(element).datepicker({
					
					closeText: 'Fechar',
	                prevText: 'Anterior',
	                nextText: 'Próximo;',
	                currentText: 'Hoje',
	                monthNames: ['Janeiro','Fevereiro','Mar&ccedil;o','Abril','Maio','Junho',
	                'Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
	                monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun',
	                'Jul','Ago','Set','Out','Nov','Dez'],
	                dayNames: ['Domingo','Segunda-feira','Ter&ccedil;a-feira','Quarta-feira','Quinta-feira','Sexta-feira','Sabado'],
	                dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sab'],
	                dayNamesMin: ['Dom','Seg','Ter','Qua','Qui','Sex','Sab'],
	                weekHeader: 'Sm',
	                
	                firstDay: 0,
	                isRTL: false,
	                showMonthAfterYear: false,
	                dateFormat: 'dd/mm/yy',
	                
					onSelect: function(date) {
						
						$scope.expenses.dateMillis = $(element).datepicker("getDate").getTime();
						$scope.expenses.date = $(element).datepicker("getDate");
						
						updateModel($(element).datepicker("getDate").getTime());						
						$(element).datepicker('hide');
						
						$(element).blur();
					    $scope.$apply();
					}					
				});
				
				if (ngModelCtrl.$viewValue != null && ngModelCtrl.$viewValue != '') {
					$(element).datepicker('setDate', ngModelCtrl.$viewValue);
				}
			}, 0);
		}
	};
	
}]);
