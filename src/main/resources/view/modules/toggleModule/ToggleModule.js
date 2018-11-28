import { api as entityModule } from '../../entity-module/GraphicEntityModule.js'

export const options = {
  decor: true,
  refreshDecor: () => {}
}

export class ToggleModule {
  constructor (assets) {
    this.interactive = {}
    this.previousFrame = {
      tooltips: {},
      paths: {},
      ownerships: {}
    }

    options.refreshDecor = () => {
      entityModule.entities.forEach(
        e => {
          if (e.currentState.image && e.currentState.image.indexOf('decor') >= 0) {
            e.graphics.visible = options.decor
          }
        })
    }
  }

  static get name () {
    return 'togglemodule'
  }

  updateScene (previousData, currentData, progress) {
    this.currentFrame = currentData
    this.currentProgress = progress
  }

  handleFrameData (frameInfo, data) {
  }

  reinitScene (container, canvasData) {
    options.refreshDecor()
  }

  animateScene (delta) {
  }

  handleGlobalData (players, globalData) {
  }
}
