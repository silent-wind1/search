<script setup lang="ts">
import { useRoute } from "vue-router";

const route = useRoute();
</script>

<template>
  <div>
    <h1>Detail Page for Item {{ route.params.id }}</h1>
    <p>Here are the details for item with ID: {{ route.params.id }}</p>
  </div>
</template>

<style scoped></style>
