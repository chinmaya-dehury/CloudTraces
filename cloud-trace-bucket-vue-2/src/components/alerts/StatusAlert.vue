<template>
  <div>
    <b-alert
        fade
        :show="dismissCountDown"
        :variant="getAlertVariant()"
        @dismissed="dismissCountDown=0"
        @dismiss-count-down="countDownChanged"
    >
      <p> {{ message || internalServerErrorMsg }} </p>
      <b-progress
          :variant="getAlertVariant()"
          :max="dismissSecs"
          :value="dismissCountDown"
          height="4px"
      />
    </b-alert>
  </div>
</template>

<script>
export default {
  name: 'StatusAlert',
  data() {
    return {
      dismissSecs: 10,
      dismissCountDown: 0,
      internalServerErrorMsg: 'Internal Server Error. Please try later or contact a system administrator.',
    }
  },
  props: {
    responseStatus: {
      type: Number,
      default: null,
    },
    message: {
      type: String,
      default: null,
    },
  },
  methods: {
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown;
    },
    getAlertVariant() {
      if (this.responseStatus === 200) {
        return 'success';
      }

      if (this.responseStatus > 200 && this.responseStatus < 500) {
        return 'warning';
      }

      if (this.responseStatus >= 500) {
        return 'danger';
      }

      return 'primary';
    },
    triggerAlert() {
      this.dismissCountDown = this.dismissSecs;
    },
  }
}
</script>

<style scoped>

</style>