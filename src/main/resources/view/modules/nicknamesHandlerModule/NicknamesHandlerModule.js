import { api as entityModule } from '../../entity-module/GraphicEntityModule.js'
import { fitAspectRatio } from '../../core/utils.js'
export const api = {
  showDamage: true,
  showCurve: false
}

export class NicknamesHandlerModule {
  constructor (assets) {
    this.mustShrinkNickname = true
    this.nicknameIds = []
  }

  static get name () {
    return 'nicks'
  }

  updateScene (previousData, currentData, progress) {
    if (this.mustShrinkNickname) {
      this.mustShrinkNickname = false
      this.nicknameIds.forEach(entityId => {
        let entity = entityModule.entities.get(entityId)
        if (!entity.currentState.text || entity.currentState.text === '') {
          this.mustShrinkNickname = true
        } else {
          if (entity.graphics.width > 270) {
            let aspectRatio = fitAspectRatio(entity.graphics.width, entity.graphics.height, 270, 50)
            entity.graphics.scale.set(aspectRatio)
          }
        }
      })
    }
  }

  handleFrameData (frameInfo, nothing) {
    return {...frameInfo}
  }

  reinitScene (container, canvasData) {
    this.mustShrinkNickname = true
  }

  animateScene (delta) {
  }

  handleGlobalData (players, nicknameIds) {
    this.nicknameIds = nicknameIds || []
  }
}
