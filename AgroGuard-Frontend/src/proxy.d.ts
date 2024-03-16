import { getCurrentInstance, ComponentInternalInstance, ComponentPublicInstance, ComponentCustomProperties } from 'vue';
export default () => (getCurrentInstance() as ComponentInternalInstance).proxy as ComponentPublicInstance;
