<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="changePictureModal" class="modal fade">
	<div class="modal-dialog">
	    <div class="modal-content">
	        <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	             <h4 class="modal-title"><spring:message code="Profile.settings.changePicture"/></h4>
	
	        </div>
	        <div class="modal-body">
	            COSAS DE CAMBIAR IMAGEN
	            <p class="text-warning"><small><spring:message code="Profile.setting.changeWarning"/></small>
	
	            </p>
	        </div>
	        <div class="modal-footer">
	            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	            <button type="button" class="btn btn-primary">Save changes</button>
	        </div>
	    </div>
	</div>
</div>