// src/fontawesome.js
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

// 将所有实心图标添加到库中
library.add(fas);

export { FontAwesomeIcon };
