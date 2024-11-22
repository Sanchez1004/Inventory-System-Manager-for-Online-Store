import React from 'react';

type FormData = Record<string, any>;

export function handleNestedChange<T extends FormData>(
  setFormData: React.Dispatch<React.SetStateAction<T | null>>,
  parentKey: keyof T,
  nestedKeys: string[]
) {
  return (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setFormData((prev) => {
      if (!prev) return null;

      if (nestedKeys.includes(name)) {
        return {
          ...prev,
          [parentKey]: {
            ...prev[parentKey],
            [name]: value,
          },
        };
      }

      return { ...prev, [name]: value };
    });
  };
}
