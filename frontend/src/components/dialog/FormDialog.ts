import {FormConfig} from "../form/FormConfig";

export class FormDialogConfig {
  title: string = "";
  form: FormConfig = new FormConfig();
  onSubmit?: (context: SubmitContext) => void;

  constructor(config?: Partial<FormDialogConfig>) {
    if (config) {
      Object.assign(this, config);
    }
  }
}

export type SubmitContext = {
  formData: any;
  actionButton: HTMLElement;
}
