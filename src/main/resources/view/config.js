import { GraphicEntityModule } from './entity-module/GraphicEntityModule.js';
import { EndScreenModule } from './modules/endScreen/EndScreenModule.js';
import { TooltipModule } from './modules/tooltip/TooltipModule.js';
import { NicknamesHandlerModule } from './modules/nicknamesHandlerModule/NicknamesHandlerModule.js';
import { options as viewOptions, ToggleModule } from './modules/toggleModule/ToggleModule.js';

// List of viewer modules that you want to use in your game
export const modules = [
	GraphicEntityModule,
	EndScreenModule,
	TooltipModule,
	ToggleModule,
	NicknamesHandlerModule
];

// Setting players' colors
export const playerColors = [
    '#d90000', // player 1
    '#41a200' // player 2
];

export const options = [{
	title: 'TILE SCENERY',
	get: function () {
	  return viewOptions.decor
	},
	set: function (value) {
	  viewOptions.decor = value
	  viewOptions.refreshDecor()
	},
	values: {
	  'ON': true,
	  'OFF': false
	}
  }];