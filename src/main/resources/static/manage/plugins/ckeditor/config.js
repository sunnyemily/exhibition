/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
    config.filebrowserImageUploadUrl='/manage/uploadEditorPicture';
    config.filebrowserUploadUrl ='/manage/uploadEditorFile';
    config.filebrowserImageBrowseUrl = '/manage/plugins/wxFile/browser.html';
    config.filebrowserFlashBrowseUrl = '/manage/plugins/wxFile/browser.html';
    config.filebrowserLinkBrowseUrl = '/manage/plugins/wxFile/browser.html';
    config.image_previewText=''; 
    config.shiftEnterMode = config.enterMode;
    config.enterMode = CKEDITOR.ENTER_BR; 
    config.allowedContent= true;
};
