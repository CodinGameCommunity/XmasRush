import { api as entityModule } from '../../entity-module/GraphicEntityModule.js'

export const options = {
  decor: false,
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
    if (this.reinit) {
      options.refreshDecor()
      this.reinit = false
    }
  }

  handleFrameData (frameInfo, data) {
  }

  reinitScene (container, canvasData) {
    this.reinit = true
  }

  animateScene (delta) {
  }

  handleGlobalData (players, globalData) {
  }
}
