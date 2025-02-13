bits 32

global start

extern exit
extern scanf
extern printf

section .data
	a dd 0
	input_format db "%d", 0
	output_format db "%d", 10, 0

section .text

start:
	push dword a
	push dword input_format
	call scanf
	add esp, 4 * 2
	
	mov eax, [a]
	imul eax, 2
	mov ebx, eax

	mov eax, 5
	imul eax, 4
	mov ecx, eax

	mov eax, ebx
	add eax, ecx
	mov ecx, eax

	mov [a], eax

	push dword [a]
	push dword output_format
	call printf
	add esp, 4 * 2
	
	push dword 0
	call exit
