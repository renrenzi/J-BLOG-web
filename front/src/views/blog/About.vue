<template>

</template>
<script>

export default {
  data() {
    return {};
  },
  created() {
    this.aboutMe();
  },
  components: {
  },
  methods: {
    aboutMe() {
      this.$router.push(
          {
            path: '/blogDetail',
            query: {
              blogId: this.$store.state.aboutId,
              blogStatus : 1
            }
          })

    }
  }
}
</script>
<style>

</style>
