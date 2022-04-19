<template>
  <div id="footer" class="container-fluid text-center bg-light p-5 ">
    <div id="footer-content">
      <p>Footer</p>
    </div>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Footer',
}
</script>

<style scoped>
body, #footer {  /* Will not work on IE if body is ommitted */
  display: flex;
  flex-direction: column;
}

#footer-content {
  flex-grow: 1;
}
</style>