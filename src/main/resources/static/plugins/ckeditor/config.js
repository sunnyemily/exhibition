/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
    config.filebrowserImageUploadUrl='/api/uploadEditorPicture';
    config.filebrowserUploadUrl ='/api/uploadEditorFile';
    config.filebrowserImageBrowseUrl = '';
    config.filebrowserFlashBrowseUrl = '';
    config.filebrowserLinkBrowseUrl = '';
    config.image_previewText=''; 
    config.shiftEnterMode = config.enterMode;
    config.enterMode = CKEDITOR.ENTER_BR; 
    //config.allowedContent= true;
	config.toolbar = 'default';
	config.toolbar = [
	        {name: 'document',items: ['RemoveFormat', '-', 'DocProps', 'Print', '-']},
	        {name: 'clipboard',items: ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo']},
	        {name: 'editing',items: ['Find', 'Replace', '-', 'SelectAll', '-']},
	        { name: 'basicstyles',items: ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-']},
	        
	        {name: 'paragraph',items: ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl']},
	        //{name: 'links',items: ['Link', 'Unlink', 'Anchor']},
	       
	        { name: 'styles',items: ['Format', 'Font', 'FontSize']},
	        { name: 'colors',items: ['TextColor', 'BGColor']},
	        {name: 'insert',items: ['Image', 'Table', 'HorizontalRule']},
	        { name: 'tools',items: []}
	    ];
};
