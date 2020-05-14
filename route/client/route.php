<?php


use think\facade\Route;
Route::get(':version/stage/allType', ':version.Stage/getStageType');
Route::get(':version/stage/all', ':version.Stage/getAllStages');
Route::get(':version/stage/info/:id', ':version.Stage/getStageById');
Route::get(':version/stage/next/:id', ':version.Stage/getNextStage');
Route::get(':version/stage/allWithTasks', ':version.Stage/getAllStagesWithTasks');

Route::get(':version/user/stage', ':version.User/getStage');
Route::get(':version/user/task', ':version.User/getTask');
Route::get(':version/user/test', ':version.Stage/test');
Route::post(":version/user/identity",":version.User/identityForWeChat");
Route::post(":version/user/add",":version.User/addUser");
Route::post(":version/user/bindCasId",":version.User/bindCasId");

Route::post(":version/user/Note/apply",":version.Note/editApplyNoteInfo");
Route::post(":version/user/Note/activist",":version.Note/editActivistNoteInfo");
Route::post(":version/user/Note/supplyActivist",":version.Note/supplyActivistNoteInfo");
Route::post(":version/user/Note/application",":version.Note/editApplicationNoteInfo");
Route::post(":version/user/Note/prepare",":version.Note/editPrepareNoteInfo");
Route::post(":version/user/Note/supplyPrepare",":version.Note/supplyPrepareNoteInfo");


Route::get(':version/task/all', ':version.Task/getAllTasks');
Route::get(':version/task/info/:id', ':version.Task/getTaskById');
Route::get(':version/task/next/:id', ':version.Task/getNextTask');
Route::get(":version/task/selectByStage/:stageId",':version.Task/getTasksByStage');

Route::post(":version/file/test",":version.File/test");
Route::post(":version/file/Note",":version.File/generateNote");
Route::post(":version/file/Material/:taskId",":version.File/uploadMaterial");

Route::get(':version/contacts/train',':version.Contacts/getTrainAll');
Route::get(':version/contacts/introduce',':version.Contacts/getIntroduceAll');
Route::post(':version/contacts/:kind',':version.Contacts/editContacts');