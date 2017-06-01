function isUndefinedOrNull(variable) {
  if (typeof variable === 'undefined' || variable === null) {
    return true;
  }

  return false;
}

function displayToastSuccess(response, toastr) {

  var obj = response.data;

  switch (response.status) {
    case 200:
      if (obj._status === 1) {
        if (obj._successMessage && obj._successMessage.length > 0)
          toastr.success(obj._successMessage.join('<br><br>'), 'Succès', {
            allowHtml: true
          });
      } else {
        if (obj._warningMessage && obj._warningMessage.length > 0)
          toastr.warning('Le formulaire est invalide', 'Erreur');
      }
      break;
    default:
      toastr.success('Status ' + response.statusText, 'Succès"');
  }
}

function displayToastError(response, toastr) {

  var obj = response.data;
  switch (response.status) {
    case 400:
      toastr.error(obj._errorMessage.join('<br><br>'), 'Erreur', {
        allowHtml: true
      });
      break;
    default:
      toastr.error(response.statusText, 'Erreur');
  }
}
