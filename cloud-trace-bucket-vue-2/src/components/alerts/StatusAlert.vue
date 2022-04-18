<template>
  <div>
    <b-alert
        :show="showAlert ? 15 : 0"
        :variant="getAlertVariant()"
        @dismissed="dismissCountDown=0"
        @dismiss-count-down="countDownChanged"
    >
      <p> {{ message }} </p>
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
    showAlert: Boolean,
  },
  methods: {
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown;
    },
    getAlertVariant() {
      if (this.responseStatus === 200) {
        return 'success';
      }

      if (this.responseStatus > 200) {
        return 'danger';
      }

      return 'primary';
    },
  }
}
</script>

<style scoped>

</style>